class Globals{}

class DevelopmentGlobals extends Globals{
    public urls = {
        administrator : "http://localhost:8080/Admin/",
        company : "http://localhost:8080/Company/",
        customer : "http://localhost:8080/Customer/",
        guest : "http://localhost:8080/Guest/"
    }
}

class ProductionGlobals extends Globals{
    public urls = {
        administrator : "/Admin/",
        company : "/Company/",
        customer : "/Customer/",
        guest : "/Guest/",
        general : "/"
    }
}

const globals = process.env.NODE_ENV === "production" ? new ProductionGlobals() : new DevelopmentGlobals();

export default globals;