# news-location-relevance

### Task
You are an engeener working in the team whose goal is to deliver local news experience to all cities in United States. 
Local experience means reading news that are relevant to the area where you live (for example if you’re in New York you would see news from New York). 

Your goal is the following: 
1. Find a free dataset of all cities in United States, create all necessary structures on the BE for it and import it as a fixed list. 
2. Find or scrape a free dataset of 100+ news articles where:
 - 80% (approximately) of dataset are local news that are relevant to different cities. 
 - 20% (approximately) of dataset are global news. 
3. Write a data pipeline with OpenAI `gpt-4o-mini` that:
 - Determines if news is local or global 
 - Determines what location the news belongs to
 - Assigns the news to a location from 1. 


### Cities dataset
https://simplemaps.com/data/us-cities
### News dataset
I couldn't find good news dataset in the web so I used python for scraping the popular local news websties in US.