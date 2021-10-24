import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import SingleCoupon from "../../mainComponents/SingleCoupon/SingleCoupon";
import Coupon from "../../models/Coupon";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import Button from '@material-ui/core/Button';
import { ArrowBackIosOutlined} from "@material-ui/icons";
import "./GetCustomerCouponsByCategory.css";

interface GetCustomerCouponsByCategoryProps{
    category:string;
}

function GetCustomerCouponsByCategory(props: GetCustomerCouponsByCategoryProps): JSX.Element {
    const [coupons, setCoupons] = useState<Coupon[]>([])
    let history = useHistory();
    
    useEffect(()=>{
        if (store.getState().authState.loginUser.clientType !== "CUSTOMER") {
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            jwtAxios.post(globals.urls.customer+"getCustomerCouponsByCategory/" + `${props.category}`)
            .then((response)=>{
                console.log(response.data);
                setCoupons(response.data);
            }).catch(error => {
                notify.error(error.response.data);
            })}}
    ,[])

    function backToHome(){
        history.push("/Customer/getCustomerCoupons");
    }
    
    return (
        <div className="GetCustomerCouponsByCategory">
            <h2>{coupons.length ===0 && <> This cusotmer has no coupons from the category {props.category}<br/></>}</h2>
			<h2>{coupons.length >0 && <>All Coupons of Category: {props.category}<hr/></>}</h2>
            <br/>
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
            <Button size="small" variant="outlined" startIcon={<ArrowBackIosOutlined/>} color="secondary" onClick={backToHome}>
                Back
            </Button>
            <br/><br/>
        </div>
    );
}

export default GetCustomerCouponsByCategory;
