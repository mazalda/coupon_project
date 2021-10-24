import { SyntheticEvent, useState } from "react";
import { useHistory } from "react-router-dom";
import SingleCustomer from "../../mainComponents/SingleCustomer/SingleCustomer";
import Customer from "../../models/Customer";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import Button from '@material-ui/core/Button';
import { Delete, Search, Send } from "@material-ui/icons";
import "./GetOneCustomer.css";

function GetOneCustomer(): JSX.Element {
    const [customer, setCustomer] = useState(new Customer());
    let history = useHistory();
    let customerId:string = "";
    
    function updateCustomerId(args:SyntheticEvent) {
        customerId = (args.target as HTMLInputElement).value.toString();
        console.log(customerId);
    }

    function searchCustomer(){
        if (store.getState().authState.loginUser.clientType !== "ADMINISTRATOR") {
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            console.log(customerId);
            jwtAxios.post(globals.urls.administrator+"getOneCustomer/" + `${customerId}`)
            .then((response)=>{
                console.log(response.data);
                setCustomer(response.data);
                notify.success("Customer was found");
            }).catch(error => {
                notify.error(error.response.data);
            })
        }
    }

    function updateCustomer(){
        history.push("/Admin/updateCustomer/" + `${customer.id}`)
    }

    function deleteCustomer(){
        jwtAxios.delete(globals.urls.administrator + "deleteCustomer/" + `${customer.id}`)
        .then(()=>{
            notify.success("Customer was deleted succesfully");
        }).catch(error => {
            notify.error(error.response.data);
        })

        history.push("/Admin/home" );
    }

    return (
        <div className="GetOneCustomer">
            <h2>Find one customer</h2><hr/>
			<br/>
			<input type="text" placeholder="Please enter company id" onChange={updateCustomerId}/>
            &nbsp;&nbsp;
            <Button size="small" variant="contained" startIcon={<Search/>} color="secondary" onClick={searchCustomer}>
                Search
            </Button>
            {customer.id != 0 && <>
                <SingleCustomer
                id={customer.id}
                firstName={customer.firstName}
                lastName={customer.lastName}
                email={customer.email}
                password={customer.password}
                coupons={customer.boughtCoupons}
                />
                <br/><br/>
                <Button size="small" variant="contained" endIcon={<Send/>} color="secondary" onClick={updateCustomer}>
                Update
                </Button>
                &nbsp;&nbsp;
                <Button size="small" variant="outlined" startIcon={<Delete/>} color="secondary" onClick={deleteCustomer}>
                Delete
                </Button>
            </>}
            <br/><br/>
        </div>
    );
}

export default GetOneCustomer;
