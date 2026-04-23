document.addEventListener('DOMContentLoaded', () => {
    loadBoardMembers();
});

async function loadBoardMembers() {
    const container = document.getElementById('board-container');
    const template = document.getElementById('board-member-template');

    try {
        const response = await fetch('/api/board');
        const members = await response.json();

        container.innerHTML = ''; 

        members.forEach(member => {
            const clone = Array.from(template.content.cloneNode(true).children);
            const domElement = clone.find(el => el.classList.contains('bg-gray-50'));

            domElement.querySelector('.member-role-title').textContent = member.role;
            domElement.querySelector('.member-id').value = member.id;
            domElement.querySelector('.member-role').value = member.role;
            domElement.querySelector('.member-name').value = member.name;

            container.appendChild(domElement);
        });

    } catch (error) {
        console.error('Erro ao carregar diretoria:', error);
        container.innerHTML = '<p class="text-red-500">Erro ao carregar dados do servidor.</p>';
    }
}

async function updateMember(event, form) {
    event.preventDefault();

    const id = form.querySelector('.member-id').value;
    const role = form.querySelector('.member-role').value;
    const name = form.querySelector('.member-name').value;

    const data = { role, name };

    try {
        const response = await fetch(`/api/board/${id}`, {
            method: 'PUT',
            headers: getAuthHeaders(),
            body: JSON.stringify(data)
        });

        if (response.ok) {
            showAlert('Diretoria atualizada com sucesso!');
        } else {
            const result = await response.json();
            showAlert(`Erro: ${result.message || 'Falha ao atualizar'}`, 'error');
        }
    } catch (error) {
        console.error('Erro ao atualizar.', error);
        showAlert('Erro de conexão ao tentar atualizar.', 'error');
    }
}