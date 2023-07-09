import TopSelectionItem from "./TopSelectionItem";
import './TopSelection.css'

const TopSelection = () => {
    return (
        <div className="top-selection">
            <h1>Top Selection</h1>
            <ul className="selection-items">
                <li>
                    <TopSelectionItem/>
                </li>
                <li>
                    <TopSelectionItem/>
                </li>
                <li>
                    <TopSelectionItem/>
                </li>
                <li>
                    <TopSelectionItem/>
                </li>
            </ul>
        </div>
    )
}

export default TopSelection;