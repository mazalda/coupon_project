import axios from "axios";
import { useEffect, useState } from "react";
import { useHistory } from "react-router";
import Coupon from "../../models/Coupon";
import globals from "../../Util/Globals";
import notify from "../../Util/Notify";
import Button from '@material-ui/core/Button';
import "./CouponDetailsForGuests.css";
import { ArrowBackIosOutlined} from "@material-ui/icons";

interface CouponDetailsForGuestsProps{
    id:string
}

function CouponDetailsForGuests(props:CouponDetailsForGuestsProps): JSX.Element {
    const [couponData, setData] = useState(new Coupon());
    let history = useHistory();

    useEffect(() => {
        axios.post(globals.urls.guest +"getOneCoupon/" + `${props.id}`).then((response) => {
            console.log(response.data);
            setData(response.data);
        }).catch(error => {
            notify.error(error.response.data);
        })
    },[])

    function backToHome(){
        history.push("/home")
    }
    
    return (
        <div className="CouponDetailsForGuests">
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
                <Button size="small" variant="outlined" startIcon={<ArrowBackIosOutlined/>} color="secondary" onClick={backToHome}>
                Back
                </Button>
            </div>
        </div>
    );
}

export default CouponDetailsForGuests;
