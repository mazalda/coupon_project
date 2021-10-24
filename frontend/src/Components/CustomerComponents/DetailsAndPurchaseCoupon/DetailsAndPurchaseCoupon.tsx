import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import Coupon from "../../models/Coupon";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import Button from '@material-ui/core/Button';
import { AddShoppingCart, ArrowBackIosOutlined} from "@material-ui/icons";
import "./DetailsAndPurchaseCoupon.css";

interface DetailsAndPurchaseCouponProps{
    id:string;
}

function DetailsAndPurchaseCoupon(props: DetailsAndPurchaseCouponProps): JSX.Element {
    const [couponData, setData] = useState(new Coupon());
    let history = useHistory();

    useEffect(() => {
        jwtAxios.post(globals.urls.customer +"getOneCoupon/" + `${props.id}`).then((response) => {
            console.log(response.data);
            setData(response.data);
        }).catch (error => {
            notify.error(error.response.data);
        })
    },[])

    function purchaseCoupon(){
        jwtAxios.post(globals.urls.customer + "purchaseCoupon/" + `${couponData.id}`)
        .then(()=>{
            notify.success("Coupon was purchased succesfully");
        }).catch(error => {
            notify.error(error.response.data);
        })

        history.push("/Customer/home");
    }

    function backToHome(){
        history.push("/Customer/home")
    }
    
    return (
        <div className="DetailsAndPurchaseCoupon">
			<br/>
            Full Coupon Details for coupon id: {props.id}<hr/>
            <div className="smallBox">
                Category: {couponData.category}<hr/>
                Title: {couponData.title}<br/>
                Description: {couponData.description}<br/>
                Start Date: {couponData.startDate}<br/>
                End Date: {couponData.endDate}<br/>
                Amount: {couponData.amount}<br/>
                Cost: {couponData.price}₪<br/><br/>
                <img src={couponData.image} width="250" height="250"/><br/><br/>
                <Button size="small" variant="outlined" startIcon={<AddShoppingCart/>} color="secondary" onClick={purchaseCoupon}>
                Purchase
                </Button>
                &nbsp;&nbsp;&nbsp;
                <Button size="small" variant="outlined" startIcon={<ArrowBackIosOutlined/>} color="secondary" onClick={backToHome}>
                Back
                </Button>
            </div>
        </div>
    );
}

export default DetailsAndPurchaseCoupon;
