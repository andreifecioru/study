const testDecorator: PropertyDecorator = (target, key) => {
    console.dir(target, { showHidden: true, depth: 2});
    console.log('Key:', key);
};

const paramDecorator: ParameterDecorator = (target, key, index) => {
    console.log(key, index);
}

const classDecorator: ClassDecorator = (ctor) => {
    console.log(`Constructor: ${ctor}`);
};

function logError(prefix: string): MethodDecorator {
    return function(
        target: Object, 
        key: string | symbol, 
        descriptor?: PropertyDescriptor): PropertyDescriptor | void {

        const method = descriptor?.value;
        if (typeof method !== 'function') return;

        descriptor!.value = function(...args: any[]) {
            try {
                method.apply(this, args);
            } catch (err) {
                console.error(`${prefix}: ${err}`)
            }
        }

        return descriptor;
    }
}


@classDecorator
class Boat {
    // @testDecorator
    color: string = 'red';

    @testDecorator
    get formattedColor(): string {
        return `This boat is ${this.color}`;
    }

    // @testDecorator
    pilot(): void {
        console.log('swish');
    }

    accelerate(
        @paramDecorator speed: number,
        @paramDecorator boost: boolean
    ): void {
        console.log(`Accelerating to ${speed}km/h (using boost: ${boost})`)
    }

    @logError('ERROR')
    throwError(): void {
        throw new Error('Something went wrong');
    }
}


const aBoat = new Boat();
console.log(aBoat.formattedColor);
aBoat.pilot();
aBoat.throwError();
aBoat.throwError();
aBoat.throwError();