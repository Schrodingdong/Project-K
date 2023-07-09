import './GetStarted.css'
import getStartedImage from '../../../static/img/Japanese calligraphy-rafiki.png'

const GetStarted = () => {
    return (
        <div className="get-started">
            <div className="get-started-content">
                <h1>Get Started</h1>
                <div className="separation-bar"></div>
                <div >
                    <ul>
                        <li>
                            <h2>Register to our website</h2>
                        </li>
                        <li>
                            <h2>Share your favourite quotes</h2>
                        </li>
                        <li>
                            <h2>Connect with other people !</h2>
                        </li>
                    </ul>
                </div>
            </div>
            <img className="get-started-img" src={getStartedImage}/>
        </div>
    )
}

export default GetStarted;