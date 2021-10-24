import { BrowserRouter } from "react-router-dom";
import Aside from "../Aside/Aside";
import Footer from "../Footer/Footer";
import Header from "../Header/Header";
import Routing from "../Routing/Routing";
import "./Layout.css";
import logo from "../../../Assets/logo.png"
import main from "../../../Assets/main.png"
import background from "../../../Assets/background.png"

function Layout(): JSX.Element {
    return (
        <div className="Layout">
            <BrowserRouter>            
                <header style={{
                    backgroundImage: `url(${logo})`,
                    backgroundPosition: 'center',
                    backgroundSize: 'cover',
                    backgroundRepeat: 'no-repeat'
                }}>
                    <Header/>
                </header>
                <aside style={{
                    backgroundImage: `url(${background})`,
                    backgroundPosition: 'center',
                    backgroundSize: 'cover',
                    backgroundRepeat: 'no-repeat'
                }}>
                    <Aside/>
                </aside>
                <main style={{
                    backgroundImage: `url(${main})`,
                    backgroundPosition: 'center',
                    backgroundSize: 'cover',
                    backgroundRepeat: 'no-repeat'
                }}>
                    <Routing/>
                </main>
                <footer style={{
                    backgroundImage: `url(${background})`,
                    backgroundPosition: 'center',
                    backgroundSize: 'cover',
                    backgroundRepeat: 'no-repeat'
                }}>
                    <Footer/>
                </footer>
            </BrowserRouter>
        </div>
    );
}

export default Layout;
