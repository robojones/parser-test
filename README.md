# Parser Test
A recursive descend parser for a simple test syntax.

## Syntax
start → stmt_list `$$`

stmt_list → stmt stmt_list | `ϵ`

stmt → `id` := expr | read `id` | write expr

expr → term term_tail

term_tail → add_op term term_tail | `ϵ`

term → factor factor_tail

factor_tail → mult_op factor factor_tail | `ϵ`

factor → `(` expr `)` | `id` | `literal`

add_op → `+` | `-`

mult_op → `*` | `/`

`literal` is defined as any number of digits (e.g. 15 or 543234).

`id` is defined as a token that does not match any other token.

For `ϵ` the syntax tree contains a node with the label `epsilon`.
