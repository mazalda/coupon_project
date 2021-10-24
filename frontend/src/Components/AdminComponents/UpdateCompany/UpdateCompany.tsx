import { useHistory } from "react-router";
import Company from "../../models/Company";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import Button from '@material-ui/core/Button';
import "./UpdateCompany.css";
import { SyntheticEvent, useEffect, useState } from "react";
import { Cancel, Send } from "@material-ui/icons";

interface UpdateCompanyProps{
    id:string;
}

function UpdateCompany(props: UpdateCompanyProps): JSX.Element {
    let history = useHistory();
    const [company, setCompany] = useState(new Company());
    let updatedEmail: string="";
    let updatedPassword:string="";

    useEffect(()=>{
        jwtAxios.post(globals.urls.administrator+"getOneCompany/" + `${props.id}`).then((response) => {
            console.log(response.data);
            setCompany(response.data);
        })
    },[])

    function updatedCompanyDetails():Company{
        let updatedCompany:Company = new Company();
        updatedCompany.id = company.id;
        updatedCompany.name = company.name;

        if(updatedEmail.length != 0){
            updatedCompany.email = updatedEmail;
        } else {
            updatedCompany.email = company.email;
        }

        if(updatedPassword.length != 0) {
            updatedCompany.password = updatedPassword;
        } else {
            updatedCompany.password = company.password;
        }
        
        return updatedCompany;
    }

    function editEmail(args:SyntheticEvent) {
        updatedEmail = (args.target as HTMLInputElement).value.toString();
    }

    function editPassword(args:SyntheticEvent) {
        updatedPassword = (args.target as HTMLInputElement).value.toString();
    }
   
    function update(){
        if(store.getState().authState.loginUser.clientType !== "ADMINISTRATOR"){
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            jwtAxios.post(globals.urls.administrator + "updateCompany", updatedCompanyDetails()).then((response) => {
                notify.success("Company was updated!");
                history.push("/Admin/home");
            }).catch(error =>{
                notify.error(error.response.data);
            })
        }
    }

    function cancel(){
        history.push("/Admin/home");
    }

    return (
        <div className="UpdateCompany displayBox">
			<h3>Update Company id: {props.id}</h3><hr/>
            <b>Company name: {company.name}</b><br/><br/>
            Company email:&nbsp;&nbsp;
            <input type="email" onChange={editEmail} defaultValue={company.email}/> <br/><br/>
            Company Password:&nbsp;&nbsp;
            <input type="text" onChange={editPassword} defaultValue={company.password}/> <br/><br/>
            <Button size="small" variant="contained" endIcon={<Send/>} color="secondary" onClick={update}>
                Update
            </Button>
            &nbsp;&nbsp;
            <Button size="small" variant="outlined" startIcon={<Cancel/>} color="secondary" onClick={cancel}>
                Cancel
            </Button>
        </div>
    );
}

export default UpdateCompany;
