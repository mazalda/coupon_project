import { NavLink } from "react-router-dom";
import "./AsideAdmin.css";

function AsideAdmin(): JSX.Element {
    return (
        <div className="AsideAdmin">
			<nav>
                <span>
                    &nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Admin/home" className="main-nav" activeClassName="main-nav-active">Home</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Admin/addCompany" className="main-nav" activeClassName="main-nav-active">Add Company</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Admin/getAllCompanies" className="main-nav" activeClassName="main-nav-active">Get All Companies</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Admin/getOneCompany/{companyId}" className="main-nav" activeClassName="main-nav-active">Get One Company</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Admin/addCustomer" className="main-nav" activeClassName="main-nav-active">Add Customer</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Admin/getAllCustomers" className="main-nav" activeClassName="main-nav-active">Get All Customers</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Admin/getOneCustomer/{customerId}" className="main-nav" activeClassName="main-nav-active">Get One Customer</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/logout" className="main-nav" activeClassName="main-nav-active">Logout</NavLink>
                </span>
            </nav>
        </div>
    );
}

export default AsideAdmin;
