
export type News = {
    id: number;
    title: string;
    content: string;
    author: string;
    date: string;
    imageUrl: string;
    sourceUrl: string;
    classification: "Global" | "Local";
    cityId: number;
}