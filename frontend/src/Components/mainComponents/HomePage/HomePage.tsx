import axios from "axios";
import { useEffect, useState } from "react";
import Coupon from "../../models/Coupon";
import globals from "../../Util/Globals";
import notify from "../../Util/Notify";
import SingleCoupon from "../SingleCoupon/SingleCoupon";
import "./HomePage.css";

function HomePage(): JSX.Element {
    
    const[coupons,setCoupons]=useState<Coupon[]>([]);

    useEffect(()=>{
        axios.post(globals.urls.guest+"getAllCoupons").then((response)=>{
            setCoupons(response.data)})
            .catch(error => {
                notify.error(error.response.data);
            })
    },[])
    
    return (
        <div className="HomePage">
            <h3>{coupons.length>0 && <> All Coupons </>}<hr/></h3>
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

export default HomePage;
