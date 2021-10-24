import Coupon from "./Coupon";

class Customer{
    id: number=0;
    firstName:string="";
    lastName:string="";
    email:string="";
    password:string="";
    boughtCoupons: Coupon[]=[];
}

export default Customer;