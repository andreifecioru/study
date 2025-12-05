import cv2
from transformers import pipeline
from PIL import Image


def draw_object_decorations(obj, frame):
    font = cv2.FONT_HERSHEY_SIMPLEX
    color = (0, 255, 255)
    stroke = 2

    box = [i for i in obj['box'].values()]

    print(f'{obj["label"]} @ {box}')

    cv2.rectangle(frame,
                  (box[0], box[1]), (box[2], box[3]),
                  color, stroke)
    
    cv2.putText(frame, f'({obj["label"]})',
                (box[0], box[1] - 8), font, 1.0, color, stroke, cv2.LINE_AA)


def main():
    stream = cv2.VideoCapture(0)

    obj_detector = pipeline(
        'object-detection',
        model='facebook/detr-resnet-50'
    )

    while (True):
        (grabbed, frame) = stream.read()

        if (grabbed):
            image = Image.fromarray(frame)
            results = obj_detector(image)
            for obj in results:
                draw_object_decorations(obj, frame)
            cv2.imshow('Live Camera', frame)
        
        key_pressed = cv2.waitKey(1) & 0xFF
        if key_pressed == ord('q'):
            break

    # cleanup
    stream.release()
    cv2.waitKey(1)
    cv2.destroyAllWindows()
    cv2.waitKey(1)

if __name__ == "__main__":
    main()