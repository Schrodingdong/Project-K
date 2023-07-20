import HeroSection from "./components/HeroSection";
import TopSelection from "./components/TopSelection";
import GetStarted from "./components/GetStarted";
import {useContext} from "react";
import {AuthContext} from "../../App";

const LandingPage = () => {
    const authContext = useContext(AuthContext);
    console.log(authContext);
    return (
        <div className="home-page">
            <HeroSection/>
            <TopSelection/>
            <GetStarted/>
        </div>
    )
}


export default LandingPage;