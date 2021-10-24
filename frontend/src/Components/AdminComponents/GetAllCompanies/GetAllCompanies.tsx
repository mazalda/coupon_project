import { useEffect, useState } from "react";
import { useHistory } from "react-router";
import SingleCompany from "../../mainComponents/SingleCompany/SingleCompany";
import Company from "../../models/Company";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import "./GetAllCompanies.css";

function GetAllCompanies(): JSX.Element {
    const [companies, setCompanies] = useState<Company[]>([]);
    let history = useHistory();
    
    useEffect(()=>{
        if (store.getState().authState.loginUser.clientType !== "ADMINISTRATOR") {
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            jwtAxios.post(globals.urls.administrator+"getAllCompanies")
            .then((response)=>{
                console.log(response.data);
                setCompanies(response.data);
            }).catch(error => {
                notify.error(error.response.data)
            })}}
    ,[])

    return (
        <div className="GetAllCompanies">
            <h2>All companies</h2><hr/>
			{companies.map(item => <SingleCompany
                key={item.id}
                id={item.id}
                name={item.name}
                email={item.email}
                password={item.password}
                coupons={item.coupons}
                />)}
        </div>
    );
}

export default GetAllCompanies;
