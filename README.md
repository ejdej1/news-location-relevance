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

Deliverable: 
- FE and BE code that allows you to search for your location from point 1. On FE we should be able to see news from those locations. Host it on AWS and share the link.
- FE - React, BE use Java.s 


Deadline: 4 working days from when the task is shared.