import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import SingleCustomer from "../../mainComponents/SingleCustomer/SingleCustomer";
import Customer from "../../models/Customer";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import "./GetCustomerDetails.css";

function GetCustomerDetails(): JSX.Element {
    const [customer, setCustomer] = useState(new Customer());
    let history = useHistory();

    useEffect(()=>{
        if (store.getState().authState.loginUser.clientType !== "CUSTOMER") {
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            jwtAxios.post(globals.urls.customer+"getCustomerDetails")
            .then((response)=>{
                console.log(response.data);
                setCustomer(response.data);
            }).catch(error => {
                notify.error(error.response.data);
            })}}
    ,[])

    return (
        <div className="GetCustomerDetails">
			<SingleCustomer
                id={customer.id}
                firstName={customer.firstName}
                lastName={customer.lastName}
                email={customer.email}
                password={customer.password}
                coupons={customer.boughtCoupons}
            />
        </div>
    );
}

export default GetCustomerDetails;
