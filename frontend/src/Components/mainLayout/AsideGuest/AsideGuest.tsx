import { NavLink } from "react-router-dom";
import "./AsideGuest.css";

function AsideGuest(): JSX.Element {
    return (
        <div className="AsideGuest">
			<nav>
                <span>
                    &nbsp;&nbsp;&nbsp;
                    <NavLink exact to="/home" className="main-nav" activeClassName="main-nav-active">Home</NavLink>
                    &nbsp;&nbsp;&nbsp;
                    <NavLink exact to="login" className="main-nav" activeClassName="main-nav-active">Login</NavLink>
                </span>
            </nav>
        </div>
    );
}

export default AsideGuest;
