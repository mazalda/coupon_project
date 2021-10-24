import { NavLink } from "react-router-dom";
import "./AsideCustomer.css";

function AsideCustomer(): JSX.Element {
    return (
        <div className="AsideCustomer">
			<nav>
                <span>
                    &nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Customer/home" className="main-nav" activeClassName="main-nav-active">Home</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Customer/getCustomerCoupons" className="main-nav" activeClassName="main-nav-active">Get Customer Coupons</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Customer/getCustomerDetails" className="main-nav" activeClassName="main-nav-active">Get Customer Details</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/logout" className="main-nav" activeClassName="main-nav-active">Logout</NavLink>
                </span>
            </nav>
        </div>
    );
}

export default AsideCustomer;
