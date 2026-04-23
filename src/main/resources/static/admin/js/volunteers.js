document.addEventListener('DOMContentLoaded', () => {
    loadVolunteerContent();

    const form = document.getElementById('volunteer-form');
    if (form) {
        form.addEventListener('submit', updateVolunteerContent);
    }
});

async function loadVolunteerContent() {
    try {
        const response = await fetch('/api/volunteer/page');
        if (response.ok) {
            const data = await response.json();
            document.getElementById('v-title1').value = data.title1 || '';
            document.getElementById('v-title2').value = data.title2 || '';
            document.getElementById('v-description').value = data.description || '';
        }
    } catch (error) {
        console.error('Erro ao carregar conteúdo de voluntários:', error);
        showAlert('Erro ao ler do banco.', 'error');
    }
}

async function updateVolunteerContent(e) {
    e.preventDefault();

    const title1 = document.getElementById('v-title1').value;
    const title2 = document.getElementById('v-title2').value;
    const description = document.getElementById('v-description').value;

    const payload = {
        title1,
        title2,
        description
    };

    try {
        const response = await fetch('/api/volunteer/page', {
            method: 'PUT',
            headers: getAuthHeaders(),
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            showAlert('Página de Voluntários atualizada com sucesso!', 'success');
        } else {
            showAlert('Falha ao atualizar textos.', 'error');
        }
    } catch (error) {
        console.error('Erro:', error);
        showAlert('Erro de conexão ao salvar.', 'error');
    }
}