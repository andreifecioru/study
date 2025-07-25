from .calculator import evaluate_expression

TOOLS = {
    'calculator': {
        'function': evaluate_expression,
        'meta': {
            'name': 'calculator',
            'description': 'Useful for evaluating simple arithmetic expressions (math).',
            'parameters': {
                'type': 'string',
                'description': 'The arithmetic expression to evaluate.'
            }
        }
    }
}

__all__ = ['TOOLS']
