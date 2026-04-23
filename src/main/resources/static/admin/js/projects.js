let projectsData = [];

document.addEventListener('DOMContentLoaded', () => {
    loadProjects();

    const form = document.getElementById('project-form');
    if (form) {
        form.addEventListener('submit', updateProject);
    }
});

async function loadProjects() {
    const grid = document.getElementById('projects-grid');
    grid.innerHTML = '<p class="col-span-full">Carregando...</p>';

    try {
        const response = await fetch('/api/projects');
        const projects = await response.json();
        projectsData = projects;
        
        grid.innerHTML = '';

        projects.forEach(project => {
            const card = document.createElement('div');
            card.className = 'bg-white overflow-hidden shadow border border-gray-200 rounded-lg cursor-pointer hover:border-blue-500 transition-colors';
            card.onclick = () => showEditForm(project.id);
            
            card.innerHTML = `
                <div class="p-5">
                    <h3 class="text-lg leading-6 font-medium text-gray-900">${project.title}</h3>
                    <p class="mt-1 max-w-2xl text-sm text-gray-500 truncate">${project.subtitle || 'Sem subtítulo'}</p>
                </div>
                <div class="bg-gray-50 px-5 py-3 border-t border-gray-200">
                    <span class="text-sm font-medium text-blue-600 hover:text-blue-500">Editar <span aria-hidden="true">&rarr;</span></span>
                </div>
            `;
            
            grid.appendChild(card);
        });

    } catch (error) {
        console.error('Erro ao carregar projetos:', error);
        grid.innerHTML = '<p class="col-span-full text-red-500">Erro ao carregar projetos.</p>';
    }
}

function showEditForm(id) {
    const project = projectsData.find(p => p.id === id);
    if (!project) return;

    document.getElementById('current-editing-title').textContent = project.title;
    document.getElementById('project-id').value = project.id;
    document.getElementById('project-code').value = project.code || '';
    document.getElementById('project-title').value = project.title || '';
    document.getElementById('project-subtitle').value = project.subtitle || '';
    document.getElementById('project-description').value = project.description || '';
    document.getElementById('project-impact').value = project.impact || '';
    document.getElementById('project-image').value = project.image || '';

    // Scroll to form
    const formCard = document.getElementById('edit-form-card');
    formCard.classList.remove('hidden');
    formCard.scrollIntoView({ behavior: 'smooth' });
}

function hideEditForm() {
    document.getElementById('edit-form-card').classList.add('hidden');
}

async function updateProject(e) {
    e.preventDefault();

    const formElement = e.target;
    const formData = new FormData(formElement);
    
    const id = formData.get('project-id');
    const projectOriginal = projectsData.find(p => p.id == id);
    
    const payload = {
        code: formData.get('code'),
        title: formData.get('title'),
        subtitle: formData.get('subtitle'),
        description: formData.get('description'),
        impact: formData.get('impact'),
        image: formData.get('image'),
        // Mantemos features e details originais pois não estão no form deste MVP
        features: projectOriginal ? projectOriginal.features : [],
        details: projectOriginal ? projectOriginal.details : {}
    };

    try {
        const response = await fetch(`/api/projects/${id}`, {
            method: 'PUT',
            headers: getAuthHeaders(),
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            showAlert('Projeto atualizado com sucesso!', 'success');
            loadProjects(); // Recarregar grid para mostrar alteração no título se houver
            hideEditForm();
        } else {
            showAlert('Falha ao atualizar o projeto.', 'error');
        }
    } catch (error) {
        console.error('Erro:', error);
        showAlert('Erro de conexão ao salvar.', 'error');
    }
}