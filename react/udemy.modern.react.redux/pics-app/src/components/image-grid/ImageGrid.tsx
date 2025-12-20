import './ImageGrid.sass';

type ImageInfo = {
  key: string | number,
  url: string,
  alt: string,
}

type ImageGridProps = {
  images: ImageInfo[]
}

function ImageGrid(props: ImageGridProps) {
  return (
    <div className="image-grid">
      <div className="image-grid__list">
        {
          props.images.map(image => {
            return (
              // NOTE: the "key" attribute must be on the top-most
              // element being rendered as the item list
              <div className="image-grid__item" key={image.key}>
                <img src={image.url} alt={image.alt}/>
              </div>
            );
          })
        }
      </div>
    </div>
  );
}

export default ImageGrid;
export {type ImageGridProps};