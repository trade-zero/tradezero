from .individual import Individual


def fitness_function(individual: Individual) -> float:
    # Exemplo de função de aptidão: a soma dos valores do cromossomo
    return float(sum(individual.chromosome))
