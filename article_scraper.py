import newspaper
import csv
import nltk
import time
nltk.download('punkt')


news_sites = [
    "https://www.cleveland.com",        
    "https://www.dallasnews.com",      
    "https://www.houstonchronicle.com",  
    "https://www.startribune.com",       
    "https://www.oregonlive.com",       
    "https://www.baltimoresun.com",     
    "https://www.nola.com",
    "https://www.freep.com",            
    "https://www.sacbee.com",      
    "https://www.azcentral.com",       
    "https://www.kansascity.com",
    "https://www.tampabay.com",         
    "https://www.tennessean.com",       
    "https://www.courant.com",           
    "https://www.dispatch.com",
    "https://www.indystar.com",         
    "https://www.sun-sentinel.com",      
    "https://www.desmoinesregister.com",
    "https://www.charlotteobserver.com",
    "https://www.sltrib.com",            
    "https://www.buffalonews.com",       
    "https://www.post-gazette.com",     
    "https://www.tulsaworld.com",        
    "https://www.arkansasonline.com",   
    "https://www.sandiegouniontribune.com", 
    "https://www.al.com",               
    "https://www.cincinnati.com",       
    "https://www.statesman.com",        
    "https://www.richmond.com",         
    "https://www.abqjournal.com",        
    "https://www.anchoragepress.com",   
    "https://www.bozemandailychronicle.com", 
    "https://www.fresnobee.com",        
    "https://www.spokesman.com",
]


articles_data = []

def scrape_site(url):
    paper = newspaper.build(url, memoize_articles=False)
    print(f"Scraping {url} with {len(paper.articles)} articles found.")

    for article in paper.articles[:70]:
        try:
            article.download()
            time.sleep(2)
            article.parse()
            article.nlp()
            
            # Extracting the data
            article_data = [
                article.title,
                article.summary,
                ", ".join(article.authors) if article.authors else "Unknown",
                article.publish_date if article.publish_date else "Unknown",
                article.top_image,
                article.url
            ]
            articles_data.append(article_data)
        except Exception as e:
            print(f"Error processing article: {e}")

for site in news_sites:
    scrape_site(site)

# Save data to a CSV file
csv_headers = ["Title", "Description", "Author", "Date", "Image URL", "Source URL"]
with open("/Users/erneststrychalski/Desktop/news_data/scrape2.csv", "w", newline="", encoding="utf-8") as f:
    writer = csv.writer(f)
    writer.writerow(csv_headers)
    writer.writerows(articles_data)

print(f"Scraped {len(articles_data)} articles and saved to 'news_articles.csv'.")