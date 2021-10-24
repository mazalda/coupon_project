import { Redirect, Route, Switch } from "react-router-dom";
import Page404 from "../../mainLayout/Page404/Page404";
import AddCoupon from "../AddCoupon/AddCoupon";
import CouponUpdate from "../CouponUpdate/CouponUpdate";
import GetCompanyCoupons from "../GetCompanyCoupons/GetCompanyCoupons";
import GetCompanyCouponsByCategory from "../GetCompanyCouponsByCategory/GetCompanyCouponsByCategory";
import GetCompanyCouponsByPrice from "../GetCompanyCouponsByPrice/GetCompanyCouponsByPrice";
import GetCompanyDetails from "../GetCompanyDetails/GetCompanyDetails";
import GetCoupon from "../GetCoupon/GetCoupon";
import "./CompanyRoute.css";

function CompanyRoute(): JSX.Element {
    return (
        <div className="CompanyRoute">
			<Switch>
                <Route path="/Company/home" component={GetCompanyCoupons} exact/>
                <Route path="/Company/addCoupon" component={AddCoupon} exact/>
                <Route path="/Company/getCoupon/:couponId" render={(props) => <GetCoupon id ={props.match.params.couponId}/>} exact/>
                <Route path="/Company/updateCoupon/:couponId" render={(props) => <CouponUpdate id={props.match.params.couponId}/>} exact/>
                <Route path="/Company/getCompanyCouponsByCategory/:category" render = {(props) => <GetCompanyCouponsByCategory category={props.match.params.category}/>} exact/>
                <Route path="/Company/getCompanyCouponsByMaxPrice/:maxPrice" render = {(props) => <GetCompanyCouponsByPrice price={props.match.params.maxPrice}/>} exact/>
                <Route path="/Company/getCompanyDetails" component={GetCompanyDetails} exact/>
                <Redirect from="/Company/" to="/Company/home" exact/>
                <Route component={Page404}/>
            </Switch>
        </div>
    );
}

export default CompanyRoute;
