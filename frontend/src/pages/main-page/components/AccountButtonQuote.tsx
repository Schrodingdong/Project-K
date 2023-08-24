import './AccountButtonQuote.css'
import {useNavigate} from "react-router-dom";

interface AccountButtonProps {
    username: string,
    userEmail: string
}

const AccountButtonQuote = (props: AccountButtonProps) => {
    // we suppose that the username is never null
    const firstLetter = props.username.toUpperCase().at(0);
    const navigate = useNavigate();
    //TODO goto profile page
    return (
        <button className="account-button" onClick={() => {navigate("/users/" + props.userEmail)}}>
            {firstLetter}
        </button>
    )
}

export default AccountButtonQuote;
