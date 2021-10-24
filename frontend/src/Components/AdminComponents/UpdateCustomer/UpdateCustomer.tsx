import { SyntheticEvent, useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import Customer from "../../models/Customer";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import Button from '@material-ui/core/Button';
import "./UpdateCustomer.css";
import { Cancel, Send } from "@material-ui/icons";

interface UpdateCustomerProps{
    id:string
}

function UpdateCustomer(props: UpdateCustomerProps): JSX.Element {
    let history = useHistory();
    const [customer, setCustomer] = useState(new Customer());
    let updatedFirstName: string="";
    let updatedLastName: string="";
    let updatedEmail: string="";
    let updatedPassword:string="";

    useEffect(()=>{
        jwtAxios.post(globals.urls.administrator+"getOneCustomer/" + `${props.id}`).then((response) => {
            console.log(response.data);
            setCustomer(response.data);
        })
    },[])

    function updatedCustomerDetails():Customer{
        let updatedCustomer:Customer = new Customer();
        updatedCustomer.id = customer.id;

        if(updatedFirstName.length != 0){
            updatedCustomer.firstName = updatedFirstName;
        } else {
            updatedCustomer.firstName = customer.firstName;
        }

        if(updatedLastName.length != 0){
            updatedCustomer.lastName = updatedLastName;
        } else {
            updatedCustomer.lastName = customer.lastName;
        }

        if(updatedEmail.length != 0){
            updatedCustomer.email = updatedEmail;
        } else {
            updatedCustomer.email = customer.email;
        }

        if(updatedPassword.length != 0) {
            updatedCustomer.password = updatedPassword;
        } else {
            updatedCustomer.password = customer.password;
        }
        
        return updatedCustomer;
    }

    function editFirstName(args:SyntheticEvent) {
        updatedFirstName = (args.target as HTMLInputElement).value.toString();
    }

    function editLastName(args:SyntheticEvent) {
        updatedLastName = (args.target as HTMLInputElement).value.toString();
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
            jwtAxios.post(globals.urls.administrator + "updateCustomer", updatedCustomerDetails()).then((response) => {
                notify.success("Customer was updated!");
                history.push("/Admin/home")
            }).catch(error =>{
                notify.error(error.response.data);
            })
        }
    }

    function cancel(){
        history.push("/Admin/home")
    }

    return (
        <div className="UpdateCustomer displayBox">
			<h3>Update Customer id: {props.id}</h3><hr/>
            Customer fisrt name:&nbsp;&nbsp;
            <input type="text" onChange={editFirstName} defaultValue={customer.firstName}/>
            <br/><br/>
            Customer last name:&nbsp;&nbsp;
            <input type="text" onChange={editLastName} defaultValue={customer.lastName}/>
            <br/><br/>
            Customer email:&nbsp;&nbsp;
            <input type="email" onChange={editEmail} defaultValue={customer.email}/>
            <br/><br/>
            Customer password:&nbsp;&nbsp;
            <input type="text" onChange={editPassword} defaultValue={customer.password}/>
            <br/><br/>
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

export default UpdateCustomer;
