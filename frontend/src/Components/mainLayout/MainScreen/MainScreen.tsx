import HomePage from "../../mainComponents/HomePage/HomePage";
import "./MainScreen.css";

function MainScreen(): JSX.Element {
    return (
        <div className="MainScreen">
            <h1>About Us</h1>
            <br/>
            <div>
                <h3>MD's coupons online store is for all food, fashion and vacations lovers! </h3>
                <h3>Here you can find the coupons for the best resturants, for the most perfect vacation places<br/>
                and the pretiest looks to wear on the trips 😉</h3>
            </div> 
            <div className="inline">
                <b>Only Now!! BLACK FRIDAY SALE on all the site!!</b><br/><br/>
                <img src="https://images.pexels.com/photos/5632378/pexels-photo-5632378.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260" width="400" height="300"/>
            </div>
            <br/><br/>
            <HomePage/>
            <br/><br/>
        </div>
    );
}

export default MainScreen;
