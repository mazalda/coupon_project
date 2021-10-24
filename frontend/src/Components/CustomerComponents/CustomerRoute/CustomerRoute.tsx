import { Redirect, Route, Switch } from "react-router-dom";
import Page404 from "../../mainLayout/Page404/Page404";
import AllCouponsToPurchase from "../AllCouponsToPurchase/AllCouponsToPurchase";
import DetailsAndPurchaseCoupon from "../DetailsAndPurchaseCoupon/DetailsAndPurchaseCoupon";
import GetCustomerCoupons from "../GetCustomerCoupons/GetCustomerCoupons";
import GetCustomerCouponsByCategory from "../GetCustomerCouponsByCategory/GetCustomerCouponsByCategory";
import GetCustomerCouponsByPrice from "../GetCustomerCouponsByPrice/GetCustomerCouponsByPrice";
import GetCustomerDetails from "../GetCustomerDetails/GetCustomerDetails";
import "./CustomerRoute.css";

function CustomerRoute(): JSX.Element {
    return (
        <div className="CustomerRoute">
			<Switch>
                <Route path="/Customer/home" component={AllCouponsToPurchase} exact/>
                <Route path="/Customer/getCustomerCoupons" component={GetCustomerCoupons} exact/>
                <Route path="/Customer/getCoupon/:couponId" render={(props) => <DetailsAndPurchaseCoupon id ={props.match.params.couponId}/>} exact/>
                <Route path="/Customer/getCustomerCouponsByCategory/:category" render={(props) => <GetCustomerCouponsByCategory category={props.match.params.category}/>} exact/>
                <Route path="/Customer/getCustomerCouponsByMaxPrice/:maxPrice" render={(props)=> <GetCustomerCouponsByPrice price={props.match.params.maxPrice}/>} exact/>
                <Route path="/Customer/getCustomerDetails" component={GetCustomerDetails} exact/>
                <Redirect from="/Customer/" to="/Customer/home" exact/>
                <Route component={Page404}/>
            </Switch>
        </div>
    );
}

export default CustomerRoute;
