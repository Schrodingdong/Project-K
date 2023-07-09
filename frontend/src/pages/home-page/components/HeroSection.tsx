import './HeroSection.css'
import bg from '../../../static/img/tile_background.png'

const HeroSection = () => {
    return (
        <div className="hero-section">
            <div className="hero-content">
                <img className="hero-bg" src={bg}/>
                <h1>“Crazy ? I was crazy once, they locked me.”</h1>
                <p>- someone ?</p>
                <div className="separation-bar"></div>
                <p>
                    Quotes are incredible. They contain wisdom, inspire us, and offer profound insights.
                    They transcend barriers, sparking conversations and guiding personal growth.
                    In just a few words, they have the power to uplift, motivate, and connect us.
                    Quotes are truly extraordinary.
                </p>
            </div>
        </div>
    )
}


export default HeroSection;