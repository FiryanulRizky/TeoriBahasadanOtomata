#variabel L1 yang dideklarasikan nilai array-nya sesuai dengan diagram transisi dari bentuk NFA
# himpunan semua string yang mengandung substring 101
L1 = {'q0': {'0': ['q0'], '1': ['q0','q1']},
       'q1': {'0': ['q2']},
       'q2': {'1': ['q3']},
       'q3': {'0': ['q3'], '1': ['q3']}}
#variabel L2 yang dideklarasikan nilai array-nya sesuai dengan diagram transisi dari bentuk NFA
# himpunan semua string yang mengandung prefiks 101
L2 = {'q0': {'1': ['q1']},
       'q1': {'0': ['q2']},
       'q2': {'1': ['q3']},
       'q3': {'0': ['q3'], '1': ['q3']}}
#variabel L3 yang dideklarasikan nilai array-nya sesuai dengan diagram transisi dari bentuk NFA
# himpunan semua string yang mengandung sufiks 101
L3 = {'q0': {'0': ['q0'], '1': ['q0','q1']},
       'q1': {'0': ['q2']},
       'q2': {'1': ['q3']},
       'q3': {}}
#variabel L4 yang dideklarasikan nilai array-nya sesuai dengan diagram transisi dari bentuk NFA
# himpunan semua string yang mengandung jumlah simbol 0 genap
L4 = {'q0': {'lamda': ['q1']},
       'q1': {'0': ['q2'], '1': ['q0','q1']},
       'q2': {'0': ['q1'], '1': ['q2']}}
#variabel L5 yang dideklarasikan nilai array-nya sesuai dengan diagram transisi dari bentuk NFA
# himpunan semua string yang mengandung jumlah simbol 1 ganjil
L5 = {'q0': {'lamda': ['q1']},
       'q1': {'0': ['q0'], '1': ['q2']},
       'q2': {'0': ['q2'], '1': ['q1']},
       'q3': {}}
#fungsi accept dengan parameter nfa, start, ends, dan str yang berfungsi
# untuk mengecek states pada NFA kemudian ditentukan panjang maximalnya yang selanjutnya akan digunakan difungsi acceptNFA
def accept(nfa, start, ends, str):
    k = 0
    #mengecek apakah terdapat simbol lamda pada transisi tersebut
    for states in nfa:
        if ('lamda' in nfa[states]):
            k = k + len(nfa[states]['lamda'])
    #menentukan panjang maksmimal
    max_length = k + (1 + k) * len(str)
    #mengembalikan nilai dengan memanggil fungsi acceptNFA
    return acceptNFA(nfa, start, ends, str, 0, 0, max_length)

#fungsi acceptNFA dengan parameter nfa, start, ends, str, i, edges, dan max_length yang berfungsi
# untuk mengecek apakah string yang diinputkan user diterima atau tidak
def acceptNFA(nfa, start, ends, str, i, edges, max_length):

    if (edges > max_length and str != ''):
        return False
    if (i >= len(str)):
        if (start in ends):
            list_vertices.append(start)
            return True
        if lamdaNFA(nfa, start, ends, str, i, edges + 1, max_length):
            return True
        return False
    if (str[i] in nfa[start]):
        nextStates = nfa[start][str[i]]
    elif lamdaNFA(nfa, start, ends, str, i, edges, max_length):
        return True
    else:
        return False
    for curr in nextStates:
        if acceptNFA(nfa, curr, ends, str, i + 1, edges + 1, max_length):
            list_vertices.append(start)
            return True
    if lamdaNFA(nfa, start, ends, str, i, edges, max_length):
        list_vertices.append(start)
        return True
    return False

#fungsi lamdaNFA dengan parameter nfa, start, ends, str, i, edges, dan max_length yang berfungsi
# untuk mengecek apakah string yang terdapat simbol lamda dapat diterima atau tidak
def lamdaNFA(nfa, start, ends, str, i, edges, max_length):
    if ('lamda' in nfa[start]):
        for curr in nfa[start]['lamda']:
            if acceptNFA(nfa, curr, ends, str, i, edges + 1, max_length):
                list_vertices.append(start)
                return True
    return False
#ini merupakan bagian main dimana user menginputkan hal-hal yang dibutuhkan
list_vertices = []
# pilihan menu yang dapat user pilih
print("MENU\n1. L1 Substring 101\n2. L2 Prefix 101\n3. L3 Suffix 101\n4. L4 0 Berjumlah Genap\n5. L5 1 Berjumlah Ganjil\n0. Keluar\n")
repeat = 1
while (repeat == 1):
    sel = input("Masukkan Pilihan: ")
    #jika user memilih pilihan 1
    if (sel == '1'):
        #user menginputkan string yang akan disek
        inputstring = input("Masukkan String: ")
        if accept(L1, 'q0', 'q3', inputstring): #memanggil fungsi accept
            print('Accepted, the traverse order is:')
            print(list(reversed(list_vertices)))
        else:
            print('Rejected')
    #jika user memilih pilihan 2
    elif (sel == '2'):
        inputstring = input("Masukkan String: ")
        if accept(L2, 'q0', 'q3', inputstring):
            print('Accepted, the traverse order is:')
            print(list(reversed(list_vertices)))
        else:
            print('Rejected')
    # jika user memilih pilihan 3
    elif (sel == '3'):
        inputstring = input("Masukkan String: ")
        if accept(L3, 'q0', 'q3', inputstring):
            print('Accepted, the traverse order is:')
            print(list(reversed(list_vertices)))
        else:
            print('Rejected')
    # jika user memilih pilihan 4
    elif (sel == '4'):
        inputstring = input("Masukkan String: ")
        if accept(L4, 'q0', 'q1', inputstring):
            print('Accepted, the traverse order is:')
            print(list(reversed(list_vertices)))
        else:
            print('Rejected')
    # jika user memilih pilihan 5
    elif (sel == '5'):
        inputstring = input("Masukkan String: ")
        if accept(L5, 'q0', 'q2', inputstring):
            print('Accepted, the traverse order is:')
            print(list(reversed(list_vertices)))
        else:
            print('Rejected')
    elif (sel == '0'):
        repeat = 0
    else:
        print("PILIHAN TIDAK TERSEDIA!")