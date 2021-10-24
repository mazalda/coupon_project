import { Redirect, Route, Switch } from "react-router-dom";
import "./Aside.css";
import AsideGuest from "../AsideGuest/AsideGuest";
import AsideAdmin from "../AsideAdmin/AsideAdmin";
import AsideCompany from "../AsideCompany/AsideCompany";
import AsideCustomer from "../AsideCustomer/AsideCustomer";

function Aside(): JSX.Element {
    return (
        <div className="Aside">
			<Switch>
                <Route path="/home" component={AsideGuest} exact/>
                <Route path="/home/*" component={AsideGuest} exact/>
                <Route path="/Admin/*" component={AsideAdmin} exact/>
                <Route path="/Company/*" component={AsideCompany} exact/>
                <Route path="/Customer/*" component={AsideCustomer} exact/>
                <Route path="/login" component={AsideGuest} exact/>
                <Redirect from="/" to="/home" exact/>
            </Switch>
        </div>
    );
}

export default Aside;
