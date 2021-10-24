import { SyntheticEvent, useState } from "react";
import { useHistory } from "react-router-dom";
import SingleCompany from "../../mainComponents/SingleCompany/SingleCompany";
import Company from "../../models/Company";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import Button from '@material-ui/core/Button';
import "./GetOneCompany.css";
import { Delete, Search, Send } from "@material-ui/icons";

function GetOneCompany(): JSX.Element {
    const [company, setCompany] = useState(new Company());
    let history = useHistory();
    let companyId:string = "";

    function updateCompanyId(args:SyntheticEvent) {
        companyId = (args.target as HTMLInputElement).value.toString();
    }

    function searchCompany(){
        if (store.getState().authState.loginUser.clientType !== "ADMINISTRATOR") {
            notify.error("You are not allowed to performe this action");
            history.push("/login");

        } else {
            jwtAxios.post(globals.urls.administrator+"getOneCompany/" + `${companyId}`)
            .then((response)=>{
                console.log(response.data);
                setCompany(response.data);
                notify.success("Company was found")
            }).catch(error => {
                notify.error(error.response.data);
            })
        }
    }

    function updateCompany(){
        history.push("/Admin/updateCompany/" + `${company.id}`);
    }

    function deleteCompany(){
        jwtAxios.delete(globals.urls.administrator + "deleteCompany/" + `${company.id}`)
        .then(()=>{
            notify.success("Company was deleted succesfully");
        }).catch(error => {
            notify.error(error.response.data);
        })

        history.push("/Admin/home" );
    }
    
    return (
        <div className="GetOneCompany">
            <h2>Find one company</h2><hr/>
            <br/>
			<input type="text" placeholder="Please enter company id" onChange={updateCompanyId}/>
            &nbsp;&nbsp;
            <Button size="small" variant="contained" startIcon={<Search/>} color="secondary" onClick={searchCompany}>
                Search
            </Button>
            {company.id!=0 && <> <SingleCompany
                id={company.id}
                name={company.name}
                email={company.email}
                password={company.password}
                coupons={company.coupons}
                /> 
                <br/><br/>
                <Button size="small" variant="contained" endIcon={<Send/>} color="secondary" onClick={updateCompany}>
                Update
                </Button>
                &nbsp;&nbsp;
                <Button size="small" variant="outlined" startIcon={<Delete/>} color="secondary" onClick={deleteCompany}>
                Delete
                </Button>
            </>}
            <br/><br/>
        </div>
    );
}

export default GetOneCompany;
