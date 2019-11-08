# Parser Test
A recursive descend parser for a simple test syntax.

### Tree

The tree contains a node for every grammar rule applied and every token consumed.
The tree node contains the name of the rule, or the token itself as label.
If `ϵ` is matched, then the node has the label "epsilon".

The tree produced by this parser is **not useful in any way**.
The purpose of this project was to learn, how a recursive descend parser works.

### Syntax

| Grammar |
|---------|
| start → stmt_list `$$`
| stmt_list → stmt stmt_list \| `ϵ`
| stmt → `id` := expr \| `read` `id` \| `write` expr
| expr → term term_tail
| term_tail → add_op term term_tail \| `ϵ`
| term → factor factor_tail
| factor_tail → mult_op factor factor_tail \| `ϵ`
| factor → `(` expr `)` \| `id` \| `literal`
| add_op → `+` \| `-`
| mult_op → `*` \| `/`
| `literal` is defined as any sequence of digits (e.g. 15 or 543234).
| `id` is defined as a token that does not match any other token.
| `ϵ` means empty

### First set
| Token | First(token) |
|-------|--------------|
| start | id, read, write, $$
| stmt_list | id, read, write, `ϵ`
| stmt | id, read, write
| expr | id, literal, (
| term_tail | +, -, `ϵ`
| term | id, literal, (
| factor_tail | *, /, `ϵ`
| factor | id, literal, (
| add_op | +, -
| mult_op | *, /

### Follow set

| Token | Follow(token) |
|-------|---------------|
| start | |
| stmt_list | $$ |
| stmt | id, read, write, $$
| expr | id, read, write, $$, )
| term_tail | id, read, write, $$, )
| term | id, read, write, $$, ), =, +, -
| factor_tail | id, read, write, $$, ), =, +, -
| factor | *, /, id, write, $$, ), =, +, -
| add_op | id, literal, (
| mult_op | id, literal, (
