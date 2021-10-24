import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import SingleCompany from "../../mainComponents/SingleCompany/SingleCompany";
import Company from "../../models/Company";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import "./GetCompanyDetails.css";

function GetCompanyDetails(): JSX.Element {
    const [company, setCompany] = useState(new Company());
    let history = useHistory();
    
    useEffect(()=>{
        if (store.getState().authState.loginUser.clientType !== "COMPANY") {
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            jwtAxios.post(globals.urls.company+"getCompanyDetails")
            .then((response)=>{
                console.log(response.data);
                setCompany(response.data);
            }).catch(error => {
                notify.error(error.response.data);
            })}}
    ,[])

    return (
        <div className="GetCompanyDetails">
			<SingleCompany
                id={company.id}
                name={company.name}
                email={company.email}
                password={company.password}
                coupons={company.coupons}
            />
        </div>
    );
}

export default GetCompanyDetails;
