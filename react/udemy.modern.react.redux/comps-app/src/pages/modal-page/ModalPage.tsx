import Button from '@/components/button/Button.tsx';
import ModalDialog from '@/components/modal/ModalDialog.tsx';
import {useCallback, useState} from 'react';

function ModalPage() {
  const [showModal, setShowModal] = useState(false);

  const handleClick = () => {
    setShowModal(true);
  }

  const handleClose = useCallback(() => {
    setShowModal(false);
  }, []);

  const modalActionBar = (
    <>
      <Button secondary outline onClick={handleClose}>Cancel</Button>
      <Button primary onClick={handleClose}>Ok</Button>
    </>
  );

  return (
    <div>
      <Button primary rounded onClick={handleClick}>Open modal</Button>
      <div>
        <p className="py-2">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam placerat lectus at purus venenatis, nec mollis erat efficitur. Sed ut risus tincidunt, volutpat nisl sed, feugiat quam. Mauris vehicula nisi urna. Curabitur semper vestibulum lorem egestas aliquet. Aliquam vehicula dolor sit amet velit dignissim volutpat. Cras dapibus nisi elit, vel consectetur nisi tincidunt eu. Maecenas maximus porta urna sed consequat. Mauris lobortis nibh et nibh rhoncus consectetur. Vivamus ac pharetra risus. Praesent convallis risus mauris, et facilisis quam vulputate sit amet. Nunc quis nunc quis nisl volutpat gravida. Quisque eget venenatis massa, et commodo est. Phasellus in pretium elit. Sed efficitur quis augue sed pulvinar. Morbi dapibus egestas justo a posuere.
        </p>
        <p className="py-2">
          Ut gravida diam eu laoreet ultricies. Nulla felis purus, elementum eget magna in, euismod molestie sapien. Etiam dui sapien, iaculis ac justo quis, luctus efficitur nisl. Morbi rhoncus metus a ex dignissim, in vestibulum lacus suscipit. Mauris non libero id felis commodo aliquam et ac leo. Suspendisse efficitur, tortor ac lobortis congue, tortor metus efficitur eros, ut efficitur diam lectus ac nunc. Morbi eget porttitor elit, quis efficitur sem. Curabitur quis ipsum tempus, placerat libero sit amet, maximus nisl. Morbi molestie rhoncus ex sed congue. Donec sodales neque eget efficitur egestas. Nulla facilisi. Proin tincidunt laoreet euismod. Suspendisse eu diam arcu.
        </p>
        <p className="py-2">
          Ut in ipsum id augue feugiat elementum id quis arcu. Sed vel feugiat libero. Quisque neque magna, porta sed porta sit amet, accumsan vel ligula. Nam est ipsum, pretium sit amet mauris non, rutrum laoreet diam. Proin sit amet ullamcorper sem, sit amet volutpat mi. In hac habitasse platea dictumst. In imperdiet, sem ac convallis tincidunt, enim lacus rutrum lacus, ut vulputate velit turpis a ipsum.
        </p>
        <p className="py-2">
          Duis facilisis velit ut turpis auctor consectetur. Sed in dui ut sem auctor rhoncus. Ut accumsan ante at arcu lobortis vestibulum. Mauris molestie eros a diam blandit, at varius orci ornare. Pellentesque in tempor turpis, id condimentum tellus. Integer sed blandit ligula, non malesuada ex. Duis eleifend nunc in rutrum vehicula. Curabitur vitae luctus magna.
        </p>
        <p className="py-2">
          Nulla vestibulum scelerisque nunc, a suscipit tellus ullamcorper a. Vivamus nec mi cursus, finibus augue id, gravida nibh. Donec scelerisque facilisis risus, at scelerisque erat. Duis at augue turpis. Morbi bibendum varius interdum. Duis porttitor sapien at dictum ultrices. Integer et turpis ut mi ullamcorper dictum et sit amet dolor. Aliquam sit amet libero vitae diam porta elementum ut ac massa. Fusce eget ante semper, rutrum nibh nec, lacinia odio. Vivamus id odio ex. Fusce dapibus, enim vel laoreet venenatis, lectus lorem vestibulum metus, eu auctor ex urna ut leo. Vivamus et aliquam sapien, in sagittis massa. Nullam fermentum, elit eget tempus aliquet, felis arcu vehicula lacus, molestie iaculis mi nulla sed arcu. In ut justo varius, consectetur odio nec, lobortis ligula.
        </p>
        <p className="py-2">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam placerat lectus at purus venenatis, nec mollis erat efficitur. Sed ut risus tincidunt, volutpat nisl sed, feugiat quam. Mauris vehicula nisi urna. Curabitur semper vestibulum lorem egestas aliquet. Aliquam vehicula dolor sit amet velit dignissim volutpat. Cras dapibus nisi elit, vel consectetur nisi tincidunt eu. Maecenas maximus porta urna sed consequat. Mauris lobortis nibh et nibh rhoncus consectetur. Vivamus ac pharetra risus. Praesent convallis risus mauris, et facilisis quam vulputate sit amet. Nunc quis nunc quis nisl volutpat gravida. Quisque eget venenatis massa, et commodo est. Phasellus in pretium elit. Sed efficitur quis augue sed pulvinar. Morbi dapibus egestas justo a posuere.
        </p>
        <p className="py-2">
          Ut gravida diam eu laoreet ultricies. Nulla felis purus, elementum eget magna in, euismod molestie sapien. Etiam dui sapien, iaculis ac justo quis, luctus efficitur nisl. Morbi rhoncus metus a ex dignissim, in vestibulum lacus suscipit. Mauris non libero id felis commodo aliquam et ac leo. Suspendisse efficitur, tortor ac lobortis congue, tortor metus efficitur eros, ut efficitur diam lectus ac nunc. Morbi eget porttitor elit, quis efficitur sem. Curabitur quis ipsum tempus, placerat libero sit amet, maximus nisl. Morbi molestie rhoncus ex sed congue. Donec sodales neque eget efficitur egestas. Nulla facilisi. Proin tincidunt laoreet euismod. Suspendisse eu diam arcu.
        </p>
        <p className="py-2">
          Ut in ipsum id augue feugiat elementum id quis arcu. Sed vel feugiat libero. Quisque neque magna, porta sed porta sit amet, accumsan vel ligula. Nam est ipsum, pretium sit amet mauris non, rutrum laoreet diam. Proin sit amet ullamcorper sem, sit amet volutpat mi. In hac habitasse platea dictumst. In imperdiet, sem ac convallis tincidunt, enim lacus rutrum lacus, ut vulputate velit turpis a ipsum.
        </p>
        <p className="py-2">
          Duis facilisis velit ut turpis auctor consectetur. Sed in dui ut sem auctor rhoncus. Ut accumsan ante at arcu lobortis vestibulum. Mauris molestie eros a diam blandit, at varius orci ornare. Pellentesque in tempor turpis, id condimentum tellus. Integer sed blandit ligula, non malesuada ex. Duis eleifend nunc in rutrum vehicula. Curabitur vitae luctus magna.
        </p>
        <p className="py-2">
          Nulla vestibulum scelerisque nunc, a suscipit tellus ullamcorper a. Vivamus nec mi cursus, finibus augue id, gravida nibh. Donec scelerisque facilisis risus, at scelerisque erat. Duis at augue turpis. Morbi bibendum varius interdum. Duis porttitor sapien at dictum ultrices. Integer et turpis ut mi ullamcorper dictum et sit amet dolor. Aliquam sit amet libero vitae diam porta elementum ut ac massa. Fusce eget ante semper, rutrum nibh nec, lacinia odio. Vivamus id odio ex. Fusce dapibus, enim vel laoreet venenatis, lectus lorem vestibulum metus, eu auctor ex urna ut leo. Vivamus et aliquam sapien, in sagittis massa. Nullam fermentum, elit eget tempus aliquet, felis arcu vehicula lacus, molestie iaculis mi nulla sed arcu. In ut justo varius, consectetur odio nec, lobortis ligula.
        </p>
        <p className="py-2">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam placerat lectus at purus venenatis, nec mollis erat efficitur. Sed ut risus tincidunt, volutpat nisl sed, feugiat quam. Mauris vehicula nisi urna. Curabitur semper vestibulum lorem egestas aliquet. Aliquam vehicula dolor sit amet velit dignissim volutpat. Cras dapibus nisi elit, vel consectetur nisi tincidunt eu. Maecenas maximus porta urna sed consequat. Mauris lobortis nibh et nibh rhoncus consectetur. Vivamus ac pharetra risus. Praesent convallis risus mauris, et facilisis quam vulputate sit amet. Nunc quis nunc quis nisl volutpat gravida. Quisque eget venenatis massa, et commodo est. Phasellus in pretium elit. Sed efficitur quis augue sed pulvinar. Morbi dapibus egestas justo a posuere.
        </p>
        <p className="py-2">
          Ut gravida diam eu laoreet ultricies. Nulla felis purus, elementum eget magna in, euismod molestie sapien. Etiam dui sapien, iaculis ac justo quis, luctus efficitur nisl. Morbi rhoncus metus a ex dignissim, in vestibulum lacus suscipit. Mauris non libero id felis commodo aliquam et ac leo. Suspendisse efficitur, tortor ac lobortis congue, tortor metus efficitur eros, ut efficitur diam lectus ac nunc. Morbi eget porttitor elit, quis efficitur sem. Curabitur quis ipsum tempus, placerat libero sit amet, maximus nisl. Morbi molestie rhoncus ex sed congue. Donec sodales neque eget efficitur egestas. Nulla facilisi. Proin tincidunt laoreet euismod. Suspendisse eu diam arcu.
        </p>
        <p className="py-2">
          Ut in ipsum id augue feugiat elementum id quis arcu. Sed vel feugiat libero. Quisque neque magna, porta sed porta sit amet, accumsan vel ligula. Nam est ipsum, pretium sit amet mauris non, rutrum laoreet diam. Proin sit amet ullamcorper sem, sit amet volutpat mi. In hac habitasse platea dictumst. In imperdiet, sem ac convallis tincidunt, enim lacus rutrum lacus, ut vulputate velit turpis a ipsum.
        </p>
        <p className="py-2">
          Duis facilisis velit ut turpis auctor consectetur. Sed in dui ut sem auctor rhoncus. Ut accumsan ante at arcu lobortis vestibulum. Mauris molestie eros a diam blandit, at varius orci ornare. Pellentesque in tempor turpis, id condimentum tellus. Integer sed blandit ligula, non malesuada ex. Duis eleifend nunc in rutrum vehicula. Curabitur vitae luctus magna.
        </p>
        <p className="py-2">
          Nulla vestibulum scelerisque nunc, a suscipit tellus ullamcorper a. Vivamus nec mi cursus, finibus augue id, gravida nibh. Donec scelerisque facilisis risus, at scelerisque erat. Duis at augue turpis. Morbi bibendum varius interdum. Duis porttitor sapien at dictum ultrices. Integer et turpis ut mi ullamcorper dictum et sit amet dolor. Aliquam sit amet libero vitae diam porta elementum ut ac massa. Fusce eget ante semper, rutrum nibh nec, lacinia odio. Vivamus id odio ex. Fusce dapibus, enim vel laoreet venenatis, lectus lorem vestibulum metus, eu auctor ex urna ut leo. Vivamus et aliquam sapien, in sagittis massa. Nullam fermentum, elit eget tempus aliquet, felis arcu vehicula lacus, molestie iaculis mi nulla sed arcu. In ut justo varius, consectetur odio nec, lobortis ligula.
        </p>
        <p className="py-2">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam placerat lectus at purus venenatis, nec mollis erat efficitur. Sed ut risus tincidunt, volutpat nisl sed, feugiat quam. Mauris vehicula nisi urna. Curabitur semper vestibulum lorem egestas aliquet. Aliquam vehicula dolor sit amet velit dignissim volutpat. Cras dapibus nisi elit, vel consectetur nisi tincidunt eu. Maecenas maximus porta urna sed consequat. Mauris lobortis nibh et nibh rhoncus consectetur. Vivamus ac pharetra risus. Praesent convallis risus mauris, et facilisis quam vulputate sit amet. Nunc quis nunc quis nisl volutpat gravida. Quisque eget venenatis massa, et commodo est. Phasellus in pretium elit. Sed efficitur quis augue sed pulvinar. Morbi dapibus egestas justo a posuere.
        </p>
        <p className="py-2">
          Ut gravida diam eu laoreet ultricies. Nulla felis purus, elementum eget magna in, euismod molestie sapien. Etiam dui sapien, iaculis ac justo quis, luctus efficitur nisl. Morbi rhoncus metus a ex dignissim, in vestibulum lacus suscipit. Mauris non libero id felis commodo aliquam et ac leo. Suspendisse efficitur, tortor ac lobortis congue, tortor metus efficitur eros, ut efficitur diam lectus ac nunc. Morbi eget porttitor elit, quis efficitur sem. Curabitur quis ipsum tempus, placerat libero sit amet, maximus nisl. Morbi molestie rhoncus ex sed congue. Donec sodales neque eget efficitur egestas. Nulla facilisi. Proin tincidunt laoreet euismod. Suspendisse eu diam arcu.
        </p>
        <p className="py-2">
          Ut in ipsum id augue feugiat elementum id quis arcu. Sed vel feugiat libero. Quisque neque magna, porta sed porta sit amet, accumsan vel ligula. Nam est ipsum, pretium sit amet mauris non, rutrum laoreet diam. Proin sit amet ullamcorper sem, sit amet volutpat mi. In hac habitasse platea dictumst. In imperdiet, sem ac convallis tincidunt, enim lacus rutrum lacus, ut vulputate velit turpis a ipsum.
        </p>
        <p className="py-2">
          Duis facilisis velit ut turpis auctor consectetur. Sed in dui ut sem auctor rhoncus. Ut accumsan ante at arcu lobortis vestibulum. Mauris molestie eros a diam blandit, at varius orci ornare. Pellentesque in tempor turpis, id condimentum tellus. Integer sed blandit ligula, non malesuada ex. Duis eleifend nunc in rutrum vehicula. Curabitur vitae luctus magna.
        </p>
        <p className="py-2">
          Nulla vestibulum scelerisque nunc, a suscipit tellus ullamcorper a. Vivamus nec mi cursus, finibus augue id, gravida nibh. Donec scelerisque facilisis risus, at scelerisque erat. Duis at augue turpis. Morbi bibendum varius interdum. Duis porttitor sapien at dictum ultrices. Integer et turpis ut mi ullamcorper dictum et sit amet dolor. Aliquam sit amet libero vitae diam porta elementum ut ac massa. Fusce eget ante semper, rutrum nibh nec, lacinia odio. Vivamus id odio ex. Fusce dapibus, enim vel laoreet venenatis, lectus lorem vestibulum metus, eu auctor ex urna ut leo. Vivamus et aliquam sapien, in sagittis massa. Nullam fermentum, elit eget tempus aliquet, felis arcu vehicula lacus, molestie iaculis mi nulla sed arcu. In ut justo varius, consectetur odio nec, lobortis ligula.
        </p>
      </div>
      {showModal && <ModalDialog
          title="Modal dialog box"
          onClose={handleClose}
          actionBar={modalActionBar}
      >
        This is a modal!
      </ModalDialog>}
    </div>
  );
}

export default ModalPage;