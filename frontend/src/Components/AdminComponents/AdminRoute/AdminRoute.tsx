import { Redirect, Route, Switch } from "react-router-dom";
import FullCouponDetails from "../../mainComponents/FullCouponDetails/FullCouponDetails";
import HomePage from "../../mainComponents/HomePage/HomePage";
import Page404 from "../../mainLayout/Page404/Page404";
import AddCompany from "../AddCompany/AddCompany";
import AddCustomer from "../AddCustomer/AddCustomer";
import GetAllCompanies from "../GetAllCompanies/GetAllCompanies";
import GetAllCustomers from "../GetAllCustomers/GetAllCustomers";
import GetOneCompany from "../GetOneCompany/GetOneCompany";
import GetOneCustomer from "../GetOneCustomer/GetOneCustomer";
import UpdateCompany from "../UpdateCompany/UpdateCompany";
import UpdateCustomer from "../UpdateCustomer/UpdateCustomer";
import "./AdminRoute.css";

function AdminRoute(): JSX.Element {
    return (
        <div className="AdminRoute">
			<Switch>
                <Route path="/Admin/home" component={HomePage} exact/>
                <Route path="/Admin/fullCouponDetails/:couponId" render={(props) => <FullCouponDetails id ={props.match.params.couponId}/>} exact/>
                <Route path="/Admin/addCompany" component={AddCompany} exact/>
                <Route path="/Admin/getAllCompanies" component={GetAllCompanies} exact/>
                <Route path="/Admin/getOneCompany/{companyId}" component={GetOneCompany} exact/>
                <Route path="/Admin/addCustomer" component={AddCustomer} exact/>
                <Route path="/Admin/getAllCustomers" component={GetAllCustomers} exact/>
                <Route path="/Admin/getOneCustomer/{customerId}" component={GetOneCustomer} exact/>
                <Route path="/Admin/updateCompany/:companyId" render={(props)=><UpdateCompany id={props.match.params.companyId}/>} exact/>
                <Route path="/Admin/updateCustomer/:customerId" render={(props)=><UpdateCustomer id={props.match.params.customerId}/>} exact/>
                <Redirect from="/Admin/" to="/Admin/home" exact/>
                <Route component={Page404}/>
            </Switch>
        </div>
    );
}

export default AdminRoute;
