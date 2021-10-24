import Coupon from "./Coupon";

class Company{
    id: number=0;
    name: string="";
    email: string="";
    password: string="";
    coupons: Coupon[]=[];
}

export default Company;