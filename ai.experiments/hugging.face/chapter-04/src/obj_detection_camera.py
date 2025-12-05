import cv2


def main():
    stream = cv2.VideoCapture(0)

    while (True):
        (grabbed, frame) = stream.read()

        if (grabbed):
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