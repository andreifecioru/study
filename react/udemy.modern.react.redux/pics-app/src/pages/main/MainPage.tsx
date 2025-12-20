import './MainPage.sass'

import {useEffect, useState} from 'react';
import SearchBar from '../../components/search-bar/SearchBar.tsx';
import ImageGrid from "../../components/image-grid/ImageGrid.tsx";
import type {PicModel} from "../../api/PicsApiClient.ts";
import PicsApiClient from "../../api/PicsApiClient.ts";

const apiClient = new PicsApiClient();

function MainPage() {
  const [searchTerm, setSearchTerm] = useState('');

  const [loading, setLoading] = useState(false);
  const [pics, setPics] = useState([] as PicModel[]);

  useEffect(() => {
    let isCancelled = false // handle race conditions

    const fetchPics = async () => {
      if (searchTerm === '') {
        setPics([]);
        return;
      }

      setLoading(true);
      try {
        const fetchedPics = await apiClient.search(searchTerm);
        if (!isCancelled) setPics(fetchedPics);
      } catch (error) {
        if (!isCancelled) console.error('Error fetching images:', error);
      } finally {
        if (!isCancelled) setLoading(false);
      }
    }

    // tell the linter that we are intentionally not waiting on this async call
    void fetchPics();

    // the cleanup function: executed just before the next render starts
    return () => {
      // we cancel any previous executions of `useEffect` that may still be in progress
      isCancelled = true;
    };
  }, [searchTerm]);

  const onSearchSubmit = (term: string): void => {
    setSearchTerm(term);
  }

  const onSwapClick = () => {
    const [first, second, ...rest] = pics;
    const reorderedPics = [second, first, ...rest];
    setPics(reorderedPics);
  }

  return (
    <div>
      <SearchBar onSearchSubmit={onSearchSubmit}/>
      <div className="main-page__swap-images">
        <button onClick={onSwapClick}
                disabled={searchTerm === ''}>
          Swap first 2 images
        </button>
      </div>
      {loading ? (
        <div className="main-page__loader">Loading images...</div>
      ) : (
        searchTerm !== '' && (
          <ImageGrid images={
            pics.map((pic, idx) => {
              return {
                key: pic.id || idx,
                url: pic.thumb_url,
                alt: pic.alt_description,
              }
            })
          }/>
        )
      )}
    </div>
  );
}

export default MainPage;