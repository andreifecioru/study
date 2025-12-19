import './MainPage.sass'

import {useEffect, useMemo, useState} from "react";
import AnimalCard, {type AnimalCardProps} from "../components/animal-card/AnimalCard.tsx";
import AnimalApiClient from "../api/AnimalApiClient.ts";
import {pickFromList} from "../utils/random.ts";

const animalApi = new AnimalApiClient();

function MainPage() {
  const [animalCardProps, setAnimalCardProps] = useState(new Map() as Map<string, AnimalCardProps>);
  const [availableAnimals, setAvailableAnimals] = useState([] as string[]);
  const [addedAnimals, setAddedAnimals] = useState([] as string[]);
  const [animalsLoaded, setAnimalsLoaded] = useState(false);


  useEffect(() => {
    const incrementLikes = (kind: string): void => {
      console.log(`${kind} received a like!`);
      const key = kind.toLowerCase();

      setAnimalCardProps(prevProps => {
        const currentProp = prevProps.get(key);
        if (!currentProp) return prevProps;

        const newProps = new Map(prevProps);
        newProps.set(key, {...currentProp, likes: currentProp.likes + 1});
        return newProps;
      });
    }

    const decrementLikes = (kind: string): void => {
      console.log(`Someone doesn't like ${kind.toLowerCase()}s!`);
      const key = kind.toLowerCase();
      setAnimalCardProps(prevProps => {
        const currentProps = prevProps.get(key);
        if (!currentProps || currentProps.likes <= 0) return prevProps;

        const newProps = new Map(prevProps);
        newProps.set(key, {...currentProps, likes: currentProps.likes - 1});
        return newProps;
      });
    }

    animalApi.fetchAll()
      .then(animalModels => {
        const transformedEntries = animalModels
          .map(animalModel => [
              animalModel.kind.toLowerCase(),
              {
                ...animalModel,
                likes: 0,
                onLikesIncrement: (kind) => incrementLikes(kind),
                onLikesDecrement: (kind) => decrementLikes(kind),
              } as AnimalCardProps
            ] as const // turns the [key, value] array into a tuple
          );
        return new Map(transformedEntries);
      })
      .then(propsMap => {
        setAnimalCardProps(propsMap);
        setAvailableAnimals([...propsMap.keys()]);
        setAnimalsLoaded(true);
      });
  }, []);

  const onAddAnimalClick = (): void => {
    const kind = pickFromList(availableAnimals);
    if (kind) {
      console.log(`Adding a new animal: ${kind}`)
      setAddedAnimals([...addedAnimals, kind]);
      setAvailableAnimals(
        availableAnimals.filter(animal => animal !== kind)
      );
    }
  }

  const addedAnimalProps = useMemo(() => {
    return addedAnimals
      .map(kind => animalCardProps.get(kind))
      .filter(prop => prop !== undefined);
  }, [animalCardProps, addedAnimals]);

  return (
    <div className="main_page">
      {animalsLoaded
        ? (
          <>
            <div className='main_page__button_container'>
              <button className='main_page__button_add_animal' onClick={onAddAnimalClick}>Add another animal</button>
            </div>
            <div className='animal-grid-container'>
              <div className='animal-grid'>
                {addedAnimalProps.map(props => (
                  <AnimalCard key={props.id} {...props} />
                ))}
              </div>
            </div>
          </>
        )
        : (
          <div>Loading animals...</div>
        )
      }
    </div>
  );
}

export default MainPage;