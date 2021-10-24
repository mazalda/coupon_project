import { NavLink } from "react-router-dom";
import "./AsideCompany.css";

function AsideCompany(): JSX.Element {
    return (
        <div className="AsideCompany">
			<nav>
                <span>
                    &nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Company/home" className="main-nav" activeClassName="main-nav-active">Home</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Company/addCoupon" className="main-nav" activeClassName="main-nav-active">Add Coupon</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/Company/getCompanyDetails" className="main-nav" activeClassName="main-nav-active">Get Company Details</NavLink>&nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/logout" className="main-nav" activeClassName="main-nav-active">Logout</NavLink>
                </span>
            </nav>
        </div>
    );
}

export default AsideCompany;
