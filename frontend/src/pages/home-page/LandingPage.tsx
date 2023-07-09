import HeroSection from "./components/HeroSection";
import TopSelection from "./components/TopSelection";
import GetStarted from "./components/GetStarted";

const LandingPage = () => {
    return (
        <div className="home-page">
            <HeroSection/>
            <TopSelection/>
            <GetStarted/>
        </div>
    )
}


export default LandingPage;