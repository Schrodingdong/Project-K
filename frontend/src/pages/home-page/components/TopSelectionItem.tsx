import './TopSelectionItem.css'
import book from '../../../static/img/book.jpg'

const TopSelectionItem = () => {
    return (
        <div className="selection-item">
            <img className="selection-book" src={book}/>
            <p>crazy ? i was crazy once, they locked me in a room, a rubber room, a rubber room full
            of rats, rats make me go crazy,crazy ? i was crazy once, they locked me in a room, a rubber room, a rubber room full
            of rats, rats make me go crazy,crazy ? i was crazy once, they locked me in a room, a rubber room, a rubber room full
            of rats, rats make me go crazy,</p>
        </div>
    )
}

export default TopSelectionItem;