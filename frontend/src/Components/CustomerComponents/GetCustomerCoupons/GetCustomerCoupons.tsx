import { useState, useEffect, SyntheticEvent } from "react";
import { useHistory } from "react-router-dom";
import Coupon from "../../models/Coupon";
import store from "../../Redux/store";
import globals from "../../Util/Globals";
import jwtAxios from "../../Util/JWTaxios";
import notify from "../../Util/Notify";
import {MenuItem, Select} from "@material-ui/core";
import Button from '@material-ui/core/Button';
import "./GetCustomerCoupons.css";
import SingleCoupon from "../../mainComponents/SingleCoupon/SingleCoupon";
import { Search } from "@material-ui/icons";

function GetCustomerCoupons(): JSX.Element {
    const [coupons, setCoupons] = useState<Coupon[]>([])
    const [maxPrice, setMaxPrice] = useState("");

    let history = useHistory();
    let categoryToFilter:string="";
    let priceToFilter:string="";

    useEffect(()=>{
        if (store.getState().authState.loginUser.clientType !== "CUSTOMER") {
            notify.error("You are not allowed to performe this action");
            history.push("/login");
        } else {
            jwtAxios.post(globals.urls.customer+"getCustomerCoupons")
            .then((response)=>{
                console.log(response.data);
                setCoupons(response.data);
            }).catch(error => {
                notify.error(error.response.data);
            })}}
    ,[])

    function selectByCategory(args:SyntheticEvent){
        categoryToFilter=(args.target as HTMLInputElement).value.toString();
        console.log(categoryToFilter);
        history.push("/Customer/getCustomerCouponsByCategory/" + `${categoryToFilter}`);
    }

    function priceToUpdate(args:SyntheticEvent){
        priceToFilter=(args.target as HTMLInputElement).value.toString();
        setMaxPrice(priceToFilter);
    }

    function selectByPrice(){
        history.push("/Customer/getCustomerCouponsByMaxPrice/" + maxPrice);
    }
    
    return (
        <div className="GetCustomerCoupons">
			<h2>All Customer's Coupons</h2><hr/>
            <form onClick={selectByCategory}>
                Select Coupons By Category: &nbsp;&nbsp;&nbsp;&nbsp;
                <Select style={{width:250}}>
                    <MenuItem value={"FOOD"}>Food</MenuItem>
                    <MenuItem value={"FASHION"}>Fashion</MenuItem>
                    <MenuItem value={"ELECTRICITY"}>Electricity</MenuItem>
                    <MenuItem value={"VACATION"}>Vacation</MenuItem>
                </Select>
            </form>
            <br/><br/>
            Select Coupons By Max Price: &nbsp;
            <input type="number" placeholder="Enter price" onChange={priceToUpdate}/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <Button size="small" variant="text" startIcon={<Search/>} color="secondary" onClick={selectByPrice}>
                Search
            </Button>
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

export default GetCustomerCoupons;
