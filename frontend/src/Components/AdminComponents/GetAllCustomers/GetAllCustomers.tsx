import { useEffect, useState } from "react";
import { useHistory } from "react-router";
import SingleCustomer from "../../mainComponents/SingleCustomer/SingleCustomer";
import Customer from "../../models/Customer";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import "./GetAllCustomers.css";

function GetAllCustomers(): JSX.Element {
    const [customers, setCustomers] = useState<Customer[]>([]);
    let history = useHistory();
    
    useEffect(()=>{
        if (store.getState().authState.loginUser.clientType !== "ADMINISTRATOR") {
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            jwtAxios.post(globals.urls.administrator+"getAllCustomers")
            .then((response)=>{
                console.log(response.data);
                setCustomers(response.data);
            }).catch(error => {
                notify.error(error.response.data)
            })}}
    ,[])

    return (
        <div className="GetAllCustomers">
			<h2>All customers</h2><hr/>
			{customers.map(item => <SingleCustomer
                key={item.id}
                id={item.id}
                firstName={item.firstName}
                lastName={item.lastName}
                email={item.email}
                password={item.password}
                coupons={item.boughtCoupons}
                />)}
        </div>
    );
}

export default GetAllCustomers;
