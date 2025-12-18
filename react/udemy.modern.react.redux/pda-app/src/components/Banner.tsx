import './Banner.sass'

type BannerProps = {
  title: string
}

function Banner({title}: BannerProps) {
  return (
      <div className="banner">
        <h1 className="banner__title">{title}</h1>
      </div>
  );
}

export default Banner;
export {type BannerProps};