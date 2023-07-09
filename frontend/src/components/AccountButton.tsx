import './AccountButton.css'

interface AccountButtonProps {
    username: string
}

const AccountButton = (props: AccountButtonProps) => {
    // we suppose that the username is never null
    const firstLetter = props.username.toUpperCase().at(0);
    return (
        <button className="account-button">
            {firstLetter}
        </button>
    )
}

export default AccountButton;