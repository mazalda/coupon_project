import { Redirect, Route, Switch } from "react-router-dom";
import Login from "../../mainComponents/Login/Login";
import Logout from "../../mainComponents/Logout/Logout";
import AdminRoute from "../../AdminComponents/AdminRoute/AdminRoute";
import Page404 from "../Page404/Page404";
import CouponDetailsForGuests from "../../mainComponents/CouponDetailsForGuests/CouponDetailsForGuests";
import MainScreen from "../MainScreen/MainScreen";
import CompanyRoute from "../../CompanyComponents/CompanyRoute/CompanyRoute";
import CustomerRoute from "../../CustomerComponents/CustomerRoute/CustomerRoute";

function Routing(): JSX.Element {
    return (
        <div className="Routing">
			<Switch>
                <Route path="/home" component={MainScreen} exact/>
                <Route path="/login" component={Login} exact/>
                <Route path="/Admin/*" component={AdminRoute} exact/>
                <Route path="/Company/*" component={CompanyRoute} exact/>
                <Route path="/Customer/*" component={CustomerRoute} exact/>
                <Route path="/logout" component={Logout} exact/>
                <Route path="/home/couponDetailsForGuests/:couponId" render={(props) => <CouponDetailsForGuests id ={props.match.params.couponId}/>} exact/> 
                <Redirect from="/" to="/home" exact/>
                <Route component={Page404}/>
            </Switch>
        </div>
    );
}

export default Routing;
