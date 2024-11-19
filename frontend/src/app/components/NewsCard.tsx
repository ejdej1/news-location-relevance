import { News } from "./types"

interface NewsCardProps {
    news: News;
}

function NewsCard({ news }: NewsCardProps) {

    const handleRedirect = () => {
        window.location.href = news.sourceUrl; 
      };

      const date = new Date(news.date);
      const formattedDate = date.toLocaleDateString('en-GB');

    return(
        <div className="w-full my-10 max-w-[1000px] shadow-xl p-10 rounded-lg m-auto">
            <h1 className="text-3xl mb-2">{news.title}</h1>
            {news.classification == "Global" ? 
            <p className="bg-green-200 rounded-lg px-5 border-2 border-green-300 w-fit my-5">{news.classification}</p>:
            <p className="bg-orange-200 rounded-lg px-5 border-2 border-orange-300 w-fit my-5">{news.classification}</p>}
            <img src={news.imageUrl}></img>
            <p className="my-10">{news.content}</p>
            <div>
                <p>Date: {formattedDate}</p>
                <p>Author: {news.author}</p>
            </div>
            <button className="text-white bg-blue-700 my-5 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800" onClick={handleRedirect} >See details</button>
        </div>
    );
}

export default NewsCard;