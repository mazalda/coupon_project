import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import Coupon from "../../models/Coupon";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import "./AllCouponsToPurchase.css";
import SingleCoupon from "../../mainComponents/SingleCoupon/SingleCoupon";

function AllCouponsToPurchase(): JSX.Element {
    const [coupons, setCoupons] = useState<Coupon[]>([])
    let history = useHistory();

    useEffect(()=>{
        if (store.getState().authState.loginUser.clientType !== "CUSTOMER") {
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            jwtAxios.post(globals.urls.customer+"getAllCoupons")
            .then((response)=>{
                console.log(response.data);
                setCoupons(response.data);
            }).catch(error => {
                notify.error(error.response.data);
            })}}
    ,[])
    
    return (
        <div className="AllCouponsToPurchase">
			<h2>All Available Coupons</h2><hr/>
            <div className="noteBox">
                !! In order to purchase a coupon press on "More Info" button !!
            </div>
            <br/><br/>
			{coupons.map(item => <SingleCoupon
                key={item.id}
                id={item.id}
                companyId={item.companyId}
                category={item.category}
                title={item.title}
                description={item.description} 
                startDate={item.startDate} 
                endDate={item.endDate} 
                amount={item.amount} 
                price={item.price} 
                image={item.image}
                />)}
            <br/><br/>
        </div>
    );
}

export default AllCouponsToPurchase;
